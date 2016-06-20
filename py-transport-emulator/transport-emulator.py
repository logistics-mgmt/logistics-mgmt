__author__ = 'Mateusz'

import sys
import time
import logging
import argparse
import requests


class ApiClient:
    def __init__(self, api_url, username, password):
        self.api_url = api_url
        self.username = username
        self.password = password
        self.session = requests.session()

    def __api_call(self, request):
        try:
            response = self.session.send(request)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as re:
            logger.exception(re)
            sys.exit(1)

    def get_active_transports(self):
        request = requests.Request('GET', '{api}/transports'.format(api=self.api_url), params={'active': 'true'},
                                   auth=(self.username, self.password))
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)

    def get_transport(self, transport_id):
        request = requests.Request('GET', '{api}/transports/{id}'.format(api=self.api_url, id=transport_id),
                                   auth=(self.username, self.password))
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)

    def get_transport_route(self, transport_id):
        request = requests.Request('GET', '{api}/transports/{id}/route'.format(api=self.api_url, id=transport_id),
                                   auth=(self.username, self.password))
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)

    def get_transport_drivers(self, transport_id):
        request = requests.Request('GET', '{api}/transports/{id}/drivers'.format(api=self.api_url, id=transport_id),
                                   auth=(self.username, self.password))
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)

    def get_transport_vehicles(self, transport_id):
        request = requests.Request('GET', '{api}/transports/{id}/vehicles'.format(api=self.api_url, id=transport_id),
                                   auth=(self.username, self.password))
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)

    def get_driver(self, driver_id):
        request = requests.Request('GET', '{api}/drivers/{id}'.format(api=self.api_url, id=driver_id),
                                   auth=(self.username, self.password))
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)

    def update_driver(self, driver_dict):
        request = requests.Request('POST', '{api}/drivers/{id}'.format(api=self.api_url, id=driver_dict['id']),
                                   auth=(self.username, self.password), json=driver_dict)
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)

    def update_vehicle(self, vehicle_dict):
        request = requests.Request('POST', '{api}/vehicles/{id}'.format(api=self.api_url, id=vehicle_dict['id']),
                                   auth=(self.username, self.password), json=vehicle_dict)
        prepared_request = self.session.prepare_request(request)
        return self.__api_call(prepared_request)


class Emulator:
    """
    Emulates vehicle movement for active transports.
    """

    def __init__(self, api_client):
        """

        Args:
            api_client (ApiClient):

        Returns: Emulator

        """
        self.api_client = api_client
        self.transports = []
        for transport_dict in api_client.get_active_transports():
            transport = Transport(transport_dict, api_client)
            self.transports.append(transport)

    def run(self, time_multiplier=1.0, time_interval=10):
        """
        Retrieves active transports list from ApiClient and calls emulate() method on each active transports.
        Args:
            time_multiplier (float): Defines time multiplier for route stage duration, effective duration
            = (real_duration * time_multiplier)
            time_interval (float): defines emulator time interval in seconds - sleep time between each
            emulator loop iteration

        """
        while True:
            # On each interval, check if new transports have appeared and add them to emulated transports list
            current_transport_ids = [transport.transport_dict['id'] for transport in self.transports]
            for transport_dict in self.api_client.get_active_transports():
                if transport_dict['id'] not in current_transport_ids:
                    transport = Transport(transport_dict, self.api_client)
                    self.transports.append(transport)

            clock = time.time()

            for transport in self.transports:
                transport.emulate(clock, time_multiplier)

            time.sleep(time_interval)


class Transport:
    """
    Wrapper for Transport object provided by logistics-mgmt API.
    Assumption: if transport has n drivers, transport has also at least n vehicles.
    """

    def __init__(self, transport_dict, api_client):
        """
        Args:
            transport_dict (dict): Transport object represented as dictionary
            api_client (ApiClient): Instance of logistics-mgmt ApiClient

        Returns: Transport

        """
        self.transport_dict = transport_dict
        if not self.transport_dict.get('drivers'):
            raise ValueError("Attempted to initialize active transport(id: {id}) without any drivers!".format(
                id=self.transport_dict['id']))

        self.vehicles = []
        self.route = Route(api_client.get_transport_route(self.transport_dict['id']))
        for i, vehicle_dict in enumerate(transport_dict['vehicles']):
            vehicle = Vehicle(vehicle_dict, transport_dict['drivers'][i], self.route, api_client)
            vehicle.clock = time.time()
            self.vehicles.append(vehicle)

        if not self.vehicles:
            raise ValueError("Attempted to initialize active transport(id: {id}) without any vehicles!".format(
                id=self.transport_dict['id']))

    def __eq__(self, other):
        if self.transport_dict['id'] == other.transport_dict['id']:
            return True

        return False

    def emulate(self, clock, time_multiplier=1.0):
        """
        Calls emulate() method for each vehicle in transport.
        Args:
            clock (float): Time passed by emulator
            time_multiplier (float): Defines time multiplier for route stage duration, effective duration
            = (real_duration * time_multiplier)

        """
        for vehicle in self.vehicles:
            vehicle.emulate(clock, time_multiplier)


class Route:
    """
    Wrapper for Route object provided by ApiClient.
    """

    def __init__(self, route_dict):
        self.route_dict = route_dict

    def get_start_location(self):
        return self.route_dict['steps'][0]

    def get_end_location(self):
        return self.route_dict['steps'][-1]

    def map_location_to_route_step(self, location):
        for step in self.route_dict['steps']:
            if step['startLocation'] == location or step['endLocation'] == location:
                return step

        return None


class Vehicle:
    """
    Wrapper for Driver and Vehicle objects provided by ApiClient.
    """

    def __init__(self, vehicle_dict, driver_dict, route, api_client):
        """

        Args:
            vehicle_dict (dict): Vehicle object represented as dictionary
            driver_dict (dict): Driver object represented as dictionary
            route (Route): Route of Transport to which vehicle belongs
            api_client (ApiClient): Instance of logistics-mgmt ApiClient

        """
        self.driver_dict = driver_dict
        self.route = route

        if driver_dict.get('latitude') and driver_dict.get('longitude'):
            self.location = {'lat': driver_dict['latitude'], 'lng': driver_dict['longitude']}
        else:
            self.location = route.get_start_location()
        self.vehicle_dict = vehicle_dict
        self.route_step = route.map_location_to_route_step(self.location) or route.get_start_location()
        self.api_client = api_client
        self.clock = None
        self.finished = False

    def update_location(self):
        """
        Updates driver and vehicle locations.

        """
        logger.info("Updating driver: {driver} in vehicle: {vehicle} location.\n"
                    "Old location: {old_loc}, new location {new_loc}.".format(
            driver=self.driver_dict['id'], vehicle=self.vehicle_dict['id'],
            new_loc=self.location, old_loc={'lat': self.driver_dict.get('latitude'),
                                            'lng': self.driver_dict.get('longitude')}))

        self.driver_dict['latitude'] = self.location['lat']
        self.driver_dict['longitude'] = self.location['lng']

        self.vehicle_dict['latitude'] = self.location['lat']
        self.vehicle_dict['longitude'] = self.location['lng']

        self.api_client.update_driver(self.driver_dict)
        self.api_client.update_vehicle(self.vehicle_dict)

    def move_forward(self):
        """
        Moves driver and vehicle to the next route step.
        """
        steps = self.route.route_dict['steps']
        next_step_index = steps.index(self.route_step) + 1
        if next_step_index > len(steps):
            raise IndexError("Vehicle {vehicle} cannot move to the next step. Attempted to move to "
                             "step #{step_id} on route with {route_len} steps.".format(vehicle=self,
                                                                                       step_id=next_step_index,
                                                                                       route_len=len(steps)))

        logger.info("Moving driver: {driver} in vehicle: {vehicle} to the next route step!".format(
            driver=self.driver_dict['id'], vehicle=self.vehicle_dict['id'], old_loc=self.location))
        self.route_step = steps[next_step_index]
        self.location = self.route_step['startLocation']
        self.update_location()

    def emulate(self, clock, time_multiplier=1.0):
        """
        Emulates vehicle movement.
        Args:
            clock (float): Time passed by emulator
            time_multiplier (float): Defines time multiplier for route stage duration, effective duration
            = (real_duration * time_multiplier)
        """

        if self.finished:
            return

        if self.route_step == self.route.get_end_location():
            self.location = self.route_step['endLocation']
            self.update_location()
            logger.info("Driver: {driver} in vehicle: {vehicle} finished his route!".format(
                driver=self.driver_dict['id'], vehicle=self.vehicle_dict['id']))
            self.finished = True
            return

        time_delta = clock - self.clock
        step_duration = self.route_step['duration']['inSeconds'] * time_multiplier

        if time_delta >= step_duration:
            self.move_forward()
            self.clock = clock


def parse_args():
    parser = argparse.ArgumentParser(description="Emulates vehicles behaviour for logistics-mgmt app.")
    parser.add_argument("-a", "--api", required=True, help="Set API URL.")
    parser.add_argument("-u", "--username", required=False, help="Set username.")
    parser.add_argument("-p", "--password", required=False, help="Set password.")
    parser.add_argument("-m", "--time-multiplier", type=float, default=0.002,
                        required=False, help="Set time multiplier.")
    parser.add_argument("-i", "--time-interval", type=int, default=5,
                        required=False, help="Set time interval in seconds.")
    return parser.parse_args()


if __name__ == '__main__':
    logging.basicConfig(level=logging.DEBUG)
    logger = logging.getLogger(__name__)

    args = parse_args()
    api_cl = ApiClient(api_url=args.api, username=args.username, password=args.password)
    emulator = Emulator(api_cl)
    emulator.run(time_multiplier=args.time_multiplier, time_interval=args.time_interval)

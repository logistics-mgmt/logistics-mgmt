#!/bin/bash

# Script for setting up essential environmental variables both locally
# and remotely on Heroku.
# Usage examples:
# 1. source configure_environment_variables.sh -d "postgres://postgres:postgres@localhost:5432/" -b <your_google_maps_browser_key> -s <your_google_maps_server_key>
# 2. source configure_environment_variables.sh # (interactive prompt)


get_heroku_config_value(){
  echo $(heroku config -s | grep $1 | cut -d '=' -f 2)
}

# Parse arguments
while getopts ":d:b:s:" opt; do
  case $opt in
  d)
    database_url="$OPTARG";;
  b)
    google_maps_browser_key="$OPTARG";;
  s)
    google_maps_server_key="$OPTARG";;
  esac
done

echo "$database_url"
if [ -z "$database_url" ]; then
  echo "Please enter DATABASE_URL parameter (e.g. \"postgres://postgres:postgres@localhost:5432/\" for localhost): "
  read database_url_input
  database_url="$database_url_input"
fi
export DATABASE_URL="$database_url"

if [ -z "$google_maps_browser_key" ]; then
  echo "Please enter your Google Maps browser key (refer to README.md for more information about this key): "
  read google_maps_browser_key_input
  google_maps_browser_key="$google_maps_browser_key_input"
fi
export GOOGLE_MAPS_BROWSER_KEY="$google_maps_browser_key"

if [ "$google_maps_browser_key" != $(get_heroku_config_value GOOGLE_MAPS_BROWSER_KEY) ]; then
  heroku config:set GOOGLE_MAPS_BROWSER_KEY="$google_maps_browser_key"
fi

if [ -z "$google_maps_server_key" ]; then
  echo "Please enter your Google Maps server key (refer to README.md for more information about this key): "
  read google_maps_server_key_input
  google_maps_server_key="$google_maps_server_key_input"
fi
export GOOGLE_MAPS_SERVER_KEY="$google_maps_server_key"

if [ "$google_maps_server_key" != $(get_heroku_config_value GOOGLE_MAPS_SERVER_KEY) ]; then
  heroku config:set GOOGLE_MAPS_SERVER_KEY="$google_maps_server_key"
fi

# clear local variables
unset database_url
unset database_url_input
unset google_maps_browser_key
unset google_maps_server_key
unset google_maps_browser_key_input
unset google_maps_server_key_input

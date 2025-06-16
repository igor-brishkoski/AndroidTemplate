#!/bin/bash

rm properties/devel/local.properties
rm properties/rolling/local.properties
rm properties/staging/local.properties
rm properties/production/local.properties

# Add Quotation marks if need!!!
{
echo 'APPLICATION_ID_SUFFIX=.devel'
echo 'APPLICATION_VERSION_SUFFIX=-devel'
echo 'BUILD_CONFIG_BASE_URL="https://jsonplaceholder.typicode.com/"'
} >> properties/devel/local.properties

{
echo 'APPLICATION_ID_SUFFIX=".staging"'
echo 'APPLICATION_VERSION_SUFFIX="-staging"'
echo 'KEYSTORE_PASSWORD=staging-keystore'
echo 'KEY_ALIAS=staging-key'
echo 'KEY_PASSWORD=staging'
echo 'BUILD_CONFIG_BASE_URL="https://jsonplaceholder.typicode.com/"'
} >> properties/staging/local.properties

{
echo 'APPLICATION_ID_SUFFIX='
echo 'APPLICATION_VERSION_SUFFIX='
echo 'KEYSTORE_PASSWORD=production-keystore'
echo 'KEY_ALIAS=production-key'
echo 'KEY_PASSWORD=production'
echo 'BUILD_CONFIG_BASE_URL="https://jsonplaceholder.typicode.com/"'
} >> properties/production/local.properties
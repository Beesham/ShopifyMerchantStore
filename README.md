# ShopifyMerchantStore

## Description

ShopifyMerchantStore is an Android application that lists the entire inventory of a merchant. The application was built to satisfy 
an internship application at Shopify.

## Demo

Demo of working application [HERE](https://drive.google.com/file/d/1pggZyzZELsWckd4-Ja0FmiLlq1A0sdAG/view?usp=sharing)
## Known Bugs/Limitations/Features(Depending on your point of view)

* No up navigation as all screens are implemented as fragments hosted by a single activity
* Searching for an item will replace the existing fragment, thus, when the back button is pressed, the previous fragment is popped
e.g if 2 searches are performed in continuity the back button will pop the 1st search's results.
* Can't search for all 3 field at once when searching for an item
* Navigating from Details to Tags then pressing back button will show All Products list on top of tags list

## Key Concepts applied

* Data persistence through `Data Provider`
* Leverage's `Shopify`'s third party API to query data
* Provides searching capabilities

## Pre-requisites

* Android SDK v27
* Android Build Tools v3.1.4
* Android Support Repository v27.1.1

## Getting Started

This project uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

### Shopify
An access token is required from Shopify in order for the app to work. You need to be a Shopify merchant in order to get one.  
The key is placed in `gradle.properties` file

## Third Party Libraries
* OkHttp/Retrofit2
* Picasso
* Simonvt Schematic Content Provider
* Espresso for Implementation Tests

## License

Copyright 2016 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.

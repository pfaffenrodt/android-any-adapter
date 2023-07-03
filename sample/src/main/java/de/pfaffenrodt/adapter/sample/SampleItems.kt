/*
 * Copyright (C) 2017 Dimitri Pfaffenrodt
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.pfaffenrodt.adapter.sample

/**
 * Created by Dimitri on 14.01.17.
 */

abstract class BaseItem<out T>(val id: Int, val value: T)

class SampleItemA(id: Int, value: String) : BaseItem<String>(id, value)
class SampleItemB(id: Int, value: Int) : BaseItem<Int>(id, value)
class SampleItemC(id: Int, value: String) : BaseItem<String>(id, value)
class SampleItemD(id: Int, value: String) : BaseItem<String>(id, value)

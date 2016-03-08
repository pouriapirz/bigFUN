/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package datatype;

/**
 * @author pouria
 */
public class StringArgument implements IArgument {

    private String value;

    public StringArgument(String s) {
        this.value = s;
    }

    @Override
    public String dump() {
        return "\"" + value + "\"";
    }

    @Override
    public TypeTag getTag() {
        return TypeTag.STRING;
    }

    @Override
    public String admPrint() {
        return "\"" + value + "\"";
    }

    public String getValue() {
        return value;
    }
}
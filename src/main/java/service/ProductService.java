package service;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ProductService {

    public void parse() {

        String json = "{\n" +
                "    \"amount\": 1.0,\n" +
                "    \"base\": \"PLN\",\n" +
                "    \"date\": \"2020-12-11\",\n" +
                "    \"rates\": {\n" +
                "        \"USD\": 0.27339\n" +
                "    }\n" +
                "}";

        GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(Foo.class, new FooDeserializer());
        Foo targetObject = gsonBldr.create().fromJson(json, Foo.class);

    }

    public class Foo {

        private double value;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    public class FooDeserializer implements JsonDeserializer<Foo> {

        @Override
        public Foo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            JsonObject jObject = jsonElement.getAsJsonObject();
            JsonObject rates = jObject.getAsJsonObject("rates");
            double value = rates.get("USD").getAsDouble();
            Object keySets = rates.keySet();

            return null;
        }
    }
}

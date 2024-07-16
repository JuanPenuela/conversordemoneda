import com.google.gson.JsonObject;

public class ObjJson {

    public static JsonObject getJsonObject(String monedaInicial, String monedaCambio, Double tasaDeCambio, Double cantidad, Double total, String registro) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Moneda local", monedaInicial);
        jsonObject.addProperty("Moneda Seleccionada",monedaCambio);
        jsonObject.addProperty("Tasa De Cambio",tasaDeCambio);
        jsonObject.addProperty("Cantidad Seleccionada", cantidad);
        jsonObject.addProperty("Total De Conversion", total);
        jsonObject.addProperty("Registro: ", registro);

        return jsonObject;
    }

}

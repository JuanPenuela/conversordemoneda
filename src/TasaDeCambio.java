public record TasaDeCambio(String base_code
        , String target_code
        , Double conversion_rate
        , Double conversion_result) {

    @Override
    public String toString() {
        return "Tasa{" +
                "base_code='" + base_code + '\'' +
                ", target_code='" + target_code + '\'' +
                ", conversion_rate=" + conversion_rate +
                ", conversion_result=" + conversion_result +
                '}';
    }

    @Override
    public String base_code() {
        return base_code;
    }

    @Override
    public Double conversion_result() {
        return conversion_result;
    }

    @Override
    public String target_code() {
        return target_code;
    }

    @Override
    public Double conversion_rate() {
        return conversion_rate;
    }

}

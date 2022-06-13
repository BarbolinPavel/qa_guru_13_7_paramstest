package tests;

public enum EnumsParams {
    IRON("Утюги"), VACUUM("Пылесосы бытовые");

    public final String technic;

    EnumsParams(String technic){
        this.technic = technic;
    }
}

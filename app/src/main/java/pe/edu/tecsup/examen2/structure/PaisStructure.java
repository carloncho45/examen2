package pe.edu.tecsup.examen2.structure;

/**
 * Created by Carlos on 13/01/2016.
 */
public class PaisStructure {
    private String NamePais;
    private String CodPais;
    private String latitud;
    private String logintud;

    public String getCodPais() {
        return CodPais;
    }

    public void setCodPais(String codPais) {
        CodPais = codPais;
    }

    public String getNamePais() {
        return NamePais;
    }

    public void setNamePais(String namePais) {
        NamePais = namePais;
    }

    @Override
    public String toString() {
        return NamePais;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLogintud() {
        return logintud;
    }

    public void setLogintud(String logintud) {
        this.logintud = logintud;
    }
}

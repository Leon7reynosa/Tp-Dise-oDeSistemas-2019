package Domain.entidades.Helpers;

import Domain.entidades.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SugerenciaHelper {

    String id = null;
    List<Prenda> listaSuperior      = new ArrayList<>();
    List<Prenda> listaInfrerior     = new ArrayList<>();
    Prenda calzado                  = new Prenda();
    Prenda prendaActiveSuperior     = new Prenda();
    Prenda prendaActiveInferior     = new Prenda();
    List<String> coloresSuperior    = new ArrayList<>();
    List<String> coloresInferior    = new ArrayList<>();
    String colorActiveSuperior      = null;
    String colorActiveInferior      = null;
    String colorCalzado             = null;
    Evento eventoSugerencia         = null;

    public CalificacionSugerencia getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(CalificacionSugerencia calificacion) {
        this.calificacion = calificacion;
    }

    CalificacionSugerencia calificacion = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SugerenciaHelper(Sugerencia sugerenciaOriginal) {

        id = Integer.toString(sugerenciaOriginal.getId());

        calificacion = sugerenciaOriginal.getCalificacion();

        listaSuperior = sugerenciaOriginal.getAtuendo().getConjuntoPrendas().stream()
                        .filter(prenda -> prenda.getCategoria().equals(Categoria.SUPERIOR)).collect(Collectors.toList());

        prendaActiveSuperior = listaSuperior.get(0);

        listaSuperior.remove(0);

        listaInfrerior = sugerenciaOriginal.getAtuendo().getConjuntoPrendas().stream()
                        .filter(prenda -> prenda.getCategoria().equals(Categoria.INFERIOR)).collect(Collectors.toList());

        prendaActiveInferior = listaInfrerior.get(0);

        calzado = sugerenciaOriginal.getAtuendo().getConjuntoPrendas().stream()
                        .filter(prenda -> prenda.getCategoria().equals(Categoria.CALZADO)).collect(Collectors.toList()).get(0);

        colorActiveSuperior = prendaActiveSuperior.getColorPrimario().getColorHexa();
        colorActiveInferior = prendaActiveInferior.getColorPrimario().getColorHexa();
        colorCalzado = calzado.getColorPrimario().getColorHexa();

        listaSuperior.forEach(prenda -> coloresSuperior.add(prenda.getColorPrimario().getColorHexa()));

        listaInfrerior.forEach(prenda -> coloresInferior.add(prenda.getColorPrimario().getColorHexa()));

        eventoSugerencia = sugerenciaOriginal.getEvento();

    }

    public List<Prenda> getListaInfrerior() {
        return listaInfrerior;
    }

    public void setListaInfrerior(List<Prenda> listaInfrerior) {
        this.listaInfrerior = listaInfrerior;
    }

    public Prenda getCalzado() {
        return calzado;
    }

    public void setCalzado(Prenda calzado) {
        this.calzado = calzado;
    }

    public Prenda getPrendaActiveSuperior() {
        return prendaActiveSuperior;
    }

    public void setPrendaActiveSuperior(Prenda prendaActiveSuperior) {
        this.prendaActiveSuperior = prendaActiveSuperior;
    }

    public Prenda getPrendaActiveInferior() {
        return prendaActiveInferior;
    }

    public void setPrendaActiveInferior(Prenda prendaActiveInferior) {
        this.prendaActiveInferior = prendaActiveInferior;
    }

    public List<String> getColoresSuperior() {
        return coloresSuperior;
    }

    public void setColoresSuperior(List<String> coloresSuperior) {
        this.coloresSuperior = coloresSuperior;
    }

    public List<String> getColoresInferior() {
        return coloresInferior;
    }

    public void setColoresInferior(List<String> coloresInferior) {
        this.coloresInferior = coloresInferior;
    }

    public String getColorActiveSuperior() {
        return colorActiveSuperior;
    }

    public void setColorActiveSuperior(String colorActiveSuperior) {
        this.colorActiveSuperior = colorActiveSuperior;
    }

    public String getColorActiveInferior() {
        return colorActiveInferior;
    }

    public void setColorActiveInferior(String colorActiveInferior) {
        this.colorActiveInferior = colorActiveInferior;
    }

    public String getColorCalzado() {
        return colorCalzado;
    }

    public void setColorCalzado(String colorCalzado) {
        this.colorCalzado = colorCalzado;
    }

    public List<Prenda> getListaSuperior() {
        return listaSuperior;
    }

    public void setListaSuperior(List<Prenda> listaSuperior) {
        this.listaSuperior = listaSuperior;
    }
}

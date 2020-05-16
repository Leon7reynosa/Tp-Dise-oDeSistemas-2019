package api_Clima;

import Domain.Clima;

import java.util.List;

public interface ClimaAPI {

    Clima getClimaActual() /*throws ExcepcionDeApi*/;
    List<Clima> getClimaDe5Dias() /*throws ExcepcionDeApi*/;

}

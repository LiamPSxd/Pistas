package mx.uv.pista;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import https.t4is_uv_mx.pistas.DELETEPistaRequest;
import https.t4is_uv_mx.pistas.DELETEPistaResponse;
import https.t4is_uv_mx.pistas.GETPistaRequest;
import https.t4is_uv_mx.pistas.GETPistaResponse;
import https.t4is_uv_mx.pistas.POSTPistaRequest;
import https.t4is_uv_mx.pistas.POSTPistaResponse;
import https.t4is_uv_mx.pistas.PUTPistaRequest;
import https.t4is_uv_mx.pistas.PUTPistaResponse;

@Endpoint
public class EndPoint{
    @Autowired
    private IPista iPista;
    private JSONObject json;

    @PayloadRoot(localPart = "GETPistaRequest", namespace = "https://t4is.uv.mx/pistas")
    @ResponsePayload
    public GETPistaResponse GET(@RequestPayload GETPistaRequest request){
        GETPistaResponse response = new GETPistaResponse();
        List<Pista> pistas = new ArrayList<Pista>();
        // String res = "{\"message\": \"Exitoso\", \"data\": [";

        if(request.getId() == -1)
            for(Pista pista : iPista.findAll()) pistas.add(pista);
            response.setStatus("Exitoso");
            response.setPista(null);
        else if(request.getId() > -1){
            Pista pista = iPista.findById(request.getId()).get();

            if(pista != null) pistas.add(pista);
            else res = "{\"message\": \"Fallido\", \"data\": [";
        }

        json = new JSONObject(res + "]}");
        response.setReturn(json.toString());

        return response;
    }

    @PayloadRoot(localPart = "POSTPistaRequest", namespace = "https://t4is.uv.mx/pistas")
    @ResponsePayload
    public POSTPistaResponse POST(@RequestPayload POSTPistaRequest request){
        POSTPistaResponse response = new POSTPistaResponse();

        Pista pista = new Pista();
        pista.setEstado(request.isEstado());
        iPista.save(pista);

        if(iPista.findById(pista.getId()).get() != null) json = new JSONObject("{\"message\": \"Exitoso\"}");
        else json = new JSONObject("{\"message\": \"Fallido\"}");

        response.setReturn(json.toString());

        return response;
    }

    @PayloadRoot(localPart = "PUTPistaRequest", namespace = "https://t4is.uv.mx/pistas")
    @ResponsePayload
    public PUTPistaResponse PUT(@RequestPayload PUTPistaRequest request){
        PUTPistaResponse response = new PUTPistaResponse();
        Pista pista = iPista.findById(request.getId()).get();

        if(pista != null){
            pista.setEstado(request.isEstado());
            iPista.save(pista);

            json = new JSONObject("{\"message\": \"Exitoso\"}");
        }else json = new JSONObject("{\"message\": \"Fallido\"}");

        response.setReturn(json.toString());

        return response;
    }

    @PayloadRoot(localPart = "DELETEPistaRequest", namespace = "https://t4is.uv.mx/pistas")
    @ResponsePayload
    public DELETEPistaResponse DELETE(@RequestPayload DELETEPistaRequest request){
        DELETEPistaResponse response = new DELETEPistaResponse();
        Pista pista = iPista.findById(request.getId()).get();

        if(pista != null){
            iPista.delete(pista);

            json = new JSONObject("{\"message\": \"Exitoso\"}");
        }else json = new JSONObject("{\"message\": \"Fallido\"}");

        response.setReturn(json.toString());

        return response;
    }
}
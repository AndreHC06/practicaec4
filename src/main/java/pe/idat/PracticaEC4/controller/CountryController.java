package pe.idat.PracticaEC4.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.PracticaEC4.entity.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
import pe.idat.PracticaEC4.repository.CountryRepository;

import java.util.List;

@Service
@Path("/country")
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;
    private static final ObjectMapper  objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountries() {
        try {
            List<Country> players = countryRepository.findAll();
            String json = objectMapper.writeValueAsString(players);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountryById(@PathParam("id") Long id) {
        Country country = countryRepository.findById(id).orElse(null);
        if (country != null) {
            try {
                String json = objectMapper.writeValueAsString(country);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } catch (JsonProcessingException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al convertir a JSON")
                        .build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Pais no encontrado\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCountry(@PathParam("id") Long id, String json) {
        try {
            Country countryUpdate = objectMapper.readValue(json, Country.class);
            Country countrie = countryRepository.findById(id).orElse(null);
            if (countrie != null) {
                countrie.setCountryName(countryUpdate.getCountryName());
                countrie.setCountryContinent(countryUpdate.getCountryContinent());
                countrie.setCountryLanguage(countryUpdate.getCountryLanguage());
                countryRepository.save(countrie);
                String responseMessage = "{\"message\":\"Pais actualizado correctamente\"}";
                return Response.status(Response.Status.OK)
                        .entity(responseMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Pais no encontrado").build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCountry(@PathParam("id") Long id) {
        Country countrie = countryRepository.findById(id).orElse(null);
        if (countrie != null) {
            countryRepository.delete(countrie);
            String responseMessage = "{\"message\":\"Pais eliminado correctamente\"}";
            return Response.status(Response.Status.NO_CONTENT)
                    .entity(responseMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Pais no encontrado\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCountry(String json) {
        try {
            Country newPlayer = objectMapper.readValue(json, Country.class);
            countryRepository.save(newPlayer);
            String createdJson = objectMapper.writeValueAsString(newPlayer);
            return Response.status(Response.Status.CREATED)
                    .entity(createdJson)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud")
                    .build();
        }
    }

}

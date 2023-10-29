package no.hvl.dat108.MinWebApp.controller;

import no.hvl.dat108.MinWebApp.model.Ansatt;
import no.hvl.dat108.MinWebApp.model.Avdeling;
import no.hvl.dat108.MinWebApp.repository.AnsattRepository;
import no.hvl.dat108.MinWebApp.repository.AvdelingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MinController {
    @Autowired
    private AvdelingRepository avdelingRepository;

    @Autowired
    private AnsattRepository ansattRepository;


    @GetMapping(value = "/avdelinger")
    @ResponseBody
    public String avdelinger() {
        return listToString(avdelingRepository.findAll());
    }


    // http://localhost:8080/avdelingId?id=1
    @GetMapping(value = "/avdelingId")
    @ResponseBody
    public String getAvdById(@RequestParam int id) {
        Optional<Avdeling> optAvd = avdelingRepository.findById(id);
        if(optAvd.isEmpty()) {
            return "Det finnes ingen avdeling med id: " + id;
        }
        return optAvd.get().toString();
    }

    @GetMapping(value = "avdelingNavn")
    @ResponseBody
    public String getAvdByNavn(@RequestParam String navn) {
        return avdelingRepository.findByNavn(navn).toString();
    }

    @PostMapping(value ="/createAvdeling")
    @ResponseBody
    public String createAvdeling(@RequestParam String navn) {
        Avdeling nyAvd = new Avdeling();
        nyAvd.setNavn(navn);
        avdelingRepository.save(nyAvd);
        return nyAvd.toString();
    }

    @DeleteMapping(value="/deleteAvdByID")
    public String deleteAvdelingById(@RequestParam int id) {
        avdelingRepository.deleteById(id);
        return "Avdeling med ID: " + id + " er blitt slettet.";
    }

    @PostMapping(value = "/createAnsatt")
    @ResponseBody
    public String createAnsatt(@RequestParam String navn, String kjonn, int manedslonn, int avdeling_id) {
        Ansatt nyAnsatt = new Ansatt();
        nyAnsatt.setNavn(navn);
        nyAnsatt.setKjonn(kjonn);
        nyAnsatt.setLonn(manedslonn);
        nyAnsatt.setAvdeling_id(avdeling_id);
        ansattRepository.save(nyAnsatt);
        return nyAnsatt.toString();
    }


    // how to use this method in the http request, example: POST /updateAnsattById?id=1&fieldToUpdate=manedslonn&updatedValue=5500
    @PostMapping(value = "/updateAnsattById")
    public ResponseEntity<String> updateAnsattById(@RequestParam Integer id, @RequestParam String fieldToUpdate, @RequestParam String updatedValue) {

        Optional<Ansatt> optionalAnsatt = ansattRepository.findById(id);
        if(optionalAnsatt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ansatt with ID: " + id + " not found");
        }
        Ansatt ansatt = optionalAnsatt.get();

        //Check with field to update and apply the update

        if("navn".equals(fieldToUpdate)) {
            ansatt.setNavn(updatedValue);
        } else if("kjonn".equals(fieldToUpdate)) {
            ansatt.setKjonn(updatedValue);
        } else if("manedslonn".equals(fieldToUpdate)) {
            ansatt.setLonn(Integer.parseInt(updatedValue));
        } else if("avdeling_id".equals(fieldToUpdate)) {
            ansatt.setAvdeling_id(Integer.parseInt(updatedValue));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid field name: " + fieldToUpdate);
        }
        ansattRepository.save(ansatt);

       return ResponseEntity.ok("Updated Ansatt with ID " + id + " - Field: '" + fieldToUpdate + "' updated to " + updatedValue);
    }

    @GetMapping(value = "/getAnsattByNavn")
    @ResponseBody
    public String getAnsattByNavn(@RequestParam String navn) {
        return ansattRepository.findByNavn(navn).toString();
    }

    @GetMapping(value="/getAnsattById")
    @ResponseBody
    public String getAnsattById(@RequestParam Integer id) {
        return ansattRepository.findById(id).toString();
    }

    @GetMapping(value = "/getAnsatte")
    @ResponseBody
    public String getAnsatte() {
        return listToString(ansattRepository.findAll());
    }


    private <T> String listToString(List<T> elementer) {
        String resultat = elementer.stream().map(T::toString).collect((Collectors.joining("\n")));
        return resultat;
    }


}

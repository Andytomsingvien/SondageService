package fr.simplon.sondageservice.controller;

import fr.simplon.sondageservice.dao.impl.SondageRepository;
import fr.simplon.sondageservice.entity.Sondage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 Cette classe est un contrôleur pour l'API de gestion des sondages.

 Elle permet de récupérer, ajouter, mettre à jour et supprimer des sondages.

 Les différentes méthodes permettent de réaliser des opérations sur la ressource "sondages".
 */
@RestController
public class Controller {

    /**

     Le référentiel des sondages utilisé pour effectuer des opérations sur la ressource "sondages".
     */
    private final SondageRepository repo;
    /**

     Constructeur de la classe Controller.
     @param repo le référentiel des sondages.
     */
    public Controller(SondageRepository repo) {
        this.repo = repo;
    }
    /**

     Récupère la liste de tous les sondages.
     @return la liste des sondages.
     */
    @GetMapping("/rest/sondages")
    public List<Sondage> getSondages() {
        return repo.findAll();
    }
    /**

     Récupère un sondage à partir de son identifiant.
     @param id l'identifiant du sondage à récupérer.
     @return le sondage correspondant à l'identifiant, ou un sondage "Inconnu" si aucun sondage correspondant n'est trouvé.
     */
    @GetMapping("/rest/sondages/{id}")
    public Sondage getSondage(@PathVariable long id){
        return repo.findById(id).orElse(new Sondage("Inconnu", "Inconnu", null,"Inconnu"));
    }
    /**

     Ajoute un nouveau sondage.
     @param newSondage le nouveau sondage à ajouter.
     @return le sondage ajouté.
     */
    @PostMapping("/rest/sondages")
    public Sondage addSondage (@RequestBody Sondage newSondage){
        return repo.save(newSondage);
    }
    /**

     Met à jour un sondage existant.
     @param newSondage les nouvelles données du sondage à mettre à jour.
     @param id l'identifiant du sondage à mettre à jour.
     @return le sondage mis à jour.
     */
    @PutMapping("/rest/sondages/{id}")
    public Sondage updateSondage(@RequestBody Sondage newSondage, @PathVariable long id){
        return repo.findById(id)
                .map(sondage -> {
                    sondage.setDescription(newSondage.getDescription());
                    sondage.setQuestion(newSondage.getQuestion());
                    sondage.setDateCreation(newSondage.getDateCreation());
                    sondage.setDateCloture(newSondage.getDateCloture());
                    sondage.setCreateur(newSondage.getCreateur());
                    return repo.save(sondage);
                })
                .orElseGet(() -> {
                    newSondage.setId(id);
                    return repo.save(newSondage);
                });
    }
    /**

     Supprime un sondage à partir de son identifiant.
     @param id l'identifiant du sondage à supprimer.
     */
    @DeleteMapping("/rest/sondages/{id}")
    public void deleteSondage(@PathVariable long id) {
        repo.deleteById(id);
    }
}







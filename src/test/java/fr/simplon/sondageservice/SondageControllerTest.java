package fr.simplon.sondageservice;

import fr.simplon.sondageservice.dao.impl.SondageRepository;
import fr.simplon.sondageservice.entity.Sondage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SondageControllerTest {
private static final String BASE_URL = "http://localhost:8080/rest/sondages";

@Autowired
private SondageRepository sondageRepo;

/*La méthode init est une méthode de configuration qui est annotée avec @BeforeEach. Cette annotation signifie
que cette méthode sera exécutée avant chaque méthode de test.
Dans cette méthode, nous supprimons tous les sondages de la base de données en appelant la méthode deleteAll()
de l'objet SondageRepository. Cela permet de s'assurer que chaque test est exécuté avec une base de données
propre et cohérente, sans aucun sondage présent dans la base de données avant le début du test.
Cette méthode est donc importante pour la mise en place d'un environnement de test cohérent et fiable, en
garantissant que chaque test est exécuté avec une base de données propre et sans interférence d'autres tests
précédents ou de données résiduelles.*/
@BeforeEach
public void init() {
        sondageRepo.deleteAll();
        }

/*
Dans la méthode testGetSondages, nous créons deux objets Sondage, les ajoutons à la base de données et envoyons
une requête HTTP GET pour récupérer la liste de tous les sondages. Nous vérifions ensuite que la réponse est OK,
qu'elle contient deux éléments et qu'elle n'est pas nulle.
*/
@Test
public void testGetSondages() {
        Sondage sondage1 = new Sondage("Sondage1", "Quelle est votre couleur préférée ?", LocalDate.now().plusDays(7), "Jean");
        Sondage sondage2 = new Sondage("Sondage2", "Quel est votre animal préféré ?", LocalDate.now().plusDays(14), "Marie");
        sondageRepo.save(sondage1);
        sondageRepo.save(sondage2);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        }

/*
La méthode testGetSondageById teste la récupération d'un sondage en utilisant son identifiant. Nous créons un
nouvel objet Sondage, l'ajoutons à la base de données, puis envoyons une requête HTTP GET pour récupérer le
sondage par son identifiant. Nous vérifions ensuite que la réponse est OK, qu'elle contient un objet non nul et
que les propriétés de cet objet correspondent à celles que nous avons spécifiées.
*/
@Test
public void testGetSondageById() {
        Sondage sondage = new Sondage("Sondage", "Quelle est votre fruit préféré ?", LocalDate.now().plusDays(7), "Jean");
        sondageRepo.save(sondage);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Sondage> response = restTemplate.getForEntity(BASE_URL + "/" + sondage.getId(), Sondage.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sondage", response.getBody().getDescription());
        assertEquals("Quelle est votre fruit préféré ?", response.getBody().getQuestion());
        assertEquals(LocalDate.now().plusDays(7), response.getBody().getDateCloture());
        assertEquals("Jean", response.getBody().getCreateur());
        }

/*
La méthode testAddSondage teste l'ajout d'un nouveau sondage en envoyant une requête HTTP POST avec un nouvel
objet Sondage. Nous vérifions ensuite que la réponse est OK, qu'elle contient un objet non nul et que les
propriétés de cet objet correspondent à celles que nous avons spécifiées.
*/
@Test
public void testAddSondage() {
        Sondage newSondage = new Sondage("Sonda", "Quelle est votre couleur préférée ?", LocalDate.now().plusDays(7), "Jean");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Sondage> request = new HttpEntity<>(newSondage, headers);

        ResponseEntity<Sondage> response = restTemplate.postForEntity(BASE_URL, request, Sondage.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sonda", response.getBody().getDescription());
        assertEquals("Quelle est votre couleur préférée ?", response.getBody().getQuestion());
        assertEquals(LocalDate.now().plusDays(7), response.getBody().getDateCloture());
        assertEquals("Jean", response.getBody().getCreateur());
        }

/*
La méthode testUpdateSondage teste la mise à jour d'un sondage existant en envoyant une requête HTTP PUT avec
un objet Sondage mis à jour. Nous vérifions ensuite que la réponse est OK, qu'elle contient un objet non nul et
que les propriétés de cet objet correspondent à celles que nous avons spécifiées.
*/
@Test
public void testUpdateSondage() {
        Sondage sondage = new Sondage("Sondage", "Quelle est votre voiture préférée ?", LocalDate.now().plusDays(7), "Jean");
        sondageRepo.save(sondage);
        Sondage updatedSondage = new Sondage("Sondage modifié", "Quelle est votre moto préféré ?", LocalDate.now().plusDays(14), "Marie");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Sondage> request = new HttpEntity<>(updatedSondage, headers);

        ResponseEntity<Sondage> response = restTemplate.exchange(BASE_URL + "/" + sondage.getId(), HttpMethod.PUT, request, Sondage.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sondage modifié", response.getBody().getDescription());
        assertEquals("Quelle est votre moto préféré ?", response.getBody().getQuestion());
        assertEquals(LocalDate.now().plusDays(14), response.getBody().getDateCloture());
        assertEquals("Marie", response.getBody().getCreateur());
        }

/*
La méthode testDeleteSondage teste la suppression d'un sondage existant en envoyant une requête HTTP DELETE
avec l'identifiant du sondage à supprimer. Nous vérifions ensuite que le sondage a bien été supprimé de la base
de données.
*/
@Test
public void testDeleteSondage() {
        Sondage sondage = new Sondage("Sondage", "Quelle est votre arme préférée ?", LocalDate.now().plusDays(7), "Jean");
        sondageRepo.save(sondage);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(BASE_URL + "/" + sondage.getId());

        Optional<Sondage> deletedSondage = sondageRepo.findById(sondage.getId());

        assertFalse(deletedSondage.isPresent());
        }}
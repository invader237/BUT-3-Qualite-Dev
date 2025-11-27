package com.iut.banque.test.modele;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Banque;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import com.iut.banque.modele.Gestionnaire;

public class TestsBanques {

	private Banque banque;
	private Client client;
	private CompteSansDecouvert compteSans;
	private CompteAvecDecouvert compteAvec;

	@Before
	public void setup() throws IllegalFormatException, IllegalOperationException {
		banque = new Banque();

		client = new Client("Dupont", "David", "1 rue des Fleurs", true,
				"d.dupont1", "pwd", "1234567890");

		compteSans = new CompteSansDecouvert("FR0123456789", 100.0, client);
		compteAvec = new CompteAvecDecouvert("FR9876543210", 50.0, 200.0, client);

		Map<String, Client> clients = new HashMap<>();
		clients.put(client.getUserId(), client);
		banque.setClients(clients);

		Map<String, Gestionnaire> gests = new HashMap<>();
		banque.setGestionnaires(gests);

		Map<String, Compte> accounts = new HashMap<>();
		accounts.put(compteSans.getNumeroCompte(), compteSans);
		accounts.put(compteAvec.getNumeroCompte(), compteAvec);
		banque.setAccounts(accounts);
	}

	@Test
	public void testGettersSettersMaps() {
		assertNotNull(banque.getClients());
		assertEquals(1, banque.getClients().size());
		assertTrue(banque.getClients().containsKey(client.getUserId()));

		assertNotNull(banque.getGestionnaires());
		assertEquals(0, banque.getGestionnaires().size());

		assertNotNull(banque.getAccounts());
		assertEquals(2, banque.getAccounts().size());
		assertTrue(banque.getAccounts().containsKey(compteSans.getNumeroCompte()));
		assertTrue(banque.getAccounts().containsKey(compteAvec.getNumeroCompte()));
	}

	@Test
	public void testCrediterCompte() throws IllegalFormatException {
		double soldeInitial = compteSans.getSolde();
		banque.crediter(compteSans, 25.0);
		assertEquals(soldeInitial + 25.0, compteSans.getSolde(), 0.0001);
	}

	@Test(expected = IllegalFormatException.class)
	public void testCrediterMontantNegatifDeclencheException() throws IllegalFormatException {
		banque.crediter(compteSans, -10.0);
	}

	@Test
	public void testDebiterCompteSansDecouvertSoldeSuffisant() throws InsufficientFundsException, IllegalFormatException {
		double soldeInitial = compteSans.getSolde();
		banque.debiter(compteSans, 40.0);
		assertEquals(soldeInitial - 40.0, compteSans.getSolde(), 0.0001);
	}

	@Test(expected = InsufficientFundsException.class)
	public void testDebiterCompteSansDecouvertInsuffisant() throws InsufficientFundsException, IllegalFormatException {
		// solde 100, débit 150 doit lever InsufficientFundsException
		banque.debiter(compteSans, 150.0);
	}

	@Test
	public void testDebiterCompteAvecDecouvertAutorise() throws InsufficientFundsException, IllegalFormatException {
		// solde 50, découvert 200, débit 220 → autorisé (reste -170)
		double soldeInitial = compteAvec.getSolde();
		banque.debiter(compteAvec, 220.0);
		assertEquals(soldeInitial - 220.0, compteAvec.getSolde(), 0.0001);
	}

	@Test(expected = InsufficientFundsException.class)
	public void testDebiterCompteAvecDecouvertAuDelàAutorise() throws InsufficientFundsException, IllegalFormatException {
		// solde 50, découvert 200, débit 260 (> 250) → exception
		banque.debiter(compteAvec, 260.0);
	}

	@Test
	public void testDeleteUserSupprimeClient() {
		assertTrue(banque.getClients().containsKey(client.getUserId()));
		banque.deleteUser(client.getUserId());
		assertFalse(banque.getClients().containsKey(client.getUserId()));
	}

	@Test
	public void testChangeDecouvertValide() throws IllegalFormatException, IllegalOperationException {
		// nouveau découvert compatible
		banque.changeDecouvert(compteAvec, 150.0);
		assertEquals(150.0, compteAvec.getDecouvertAutorise(), 0.0001);
	}

	@Test(expected = IllegalFormatException.class)
	public void testChangeDecouvertNegatifDeclencheException() throws IllegalFormatException, IllegalOperationException {
		banque.changeDecouvert(compteAvec, -1.0);
	}

	@Test(expected = IllegalOperationException.class)
	public void testChangeDecouvertIncompatibleAvecSoldeActuel() throws IllegalFormatException, IllegalOperationException, InsufficientFundsException {
		// Mettre le compte en négatif puis réduire le découvert en dessous de |solde|
		banque.debiter(compteAvec, 80.0); // solde passe de 50 à -30
		banque.changeDecouvert(compteAvec, 20.0); // incompatible avec solde -30
	}
}
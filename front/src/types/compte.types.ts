/**
 * DTO returned by the Comptes API.
 * Matches the backend CompteDto YAML schema.
 */
export interface CompteDto {
	id: string;
	solde: number;
	decouvertAutorise: number;
	clientId: string;
}

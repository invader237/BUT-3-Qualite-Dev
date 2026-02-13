/**
 * DTO returned by GET /me
 * Matches the backend UtilisateurDto YAML schema.
 */
export interface UtilisateurDto {
	userId: string;
	nom: string;
	prenom: string;
	adresse?: string;
	male: boolean;
	type: string;
}

/**
 * Request body for POST /auth/login
 */
export interface LoginRequest {
	userId: string;
	password: string;
}

/**
 * Response body from POST /auth/login
 */
export interface TokenResponse {
	token: string;
}

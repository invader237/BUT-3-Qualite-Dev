const TOKEN_KEY = "auth_token";

/**
 * Simple abstraction over localStorage for the JWT token.
 * Centralised so the storage mechanism can be swapped easily.
 */
export const tokenStorage = {
	get(): string | null {
		return localStorage.getItem(TOKEN_KEY);
	},
	set(token: string): void {
		localStorage.setItem(TOKEN_KEY, token);
	},
	remove(): void {
		localStorage.removeItem(TOKEN_KEY);
	},
};

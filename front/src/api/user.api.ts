import { axiosInstance } from "@/api/axiosInstance";
import type { UtilisateurDto, LoginRequest, TokenResponse } from "@/types";

/**
 * API functions for the "user" entity.
 * Contains all endpoints related to authentication & user info.
 */
export const userApi = {
	/** POST /auth/login – authenticate and receive a JWT */
	login(payload: LoginRequest) {
		return axiosInstance.post<TokenResponse>("/auth/login", payload);
	},

	/** GET /me – retrieve the currently authenticated user */
	getMe() {
		return axiosInstance.get<UtilisateurDto>("/auth/login");
	},

	/** GET /utilisateurs/{userId} – retrieve a user by ID (manager only) */
	getUtilisateur(userId: string) {
		return axiosInstance.get<UtilisateurDto>(`/utilisateurs/${userId}`);
	},
};

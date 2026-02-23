import { axiosInstance } from "@/api/axiosInstance";
import type { CompteDto } from "@/types";

/**
 * API functions for the "compte" entity.
 * Contains all endpoints related to bank accounts.
 */
export const compteApi = {
	/** GET /comptes/all – retrieve all accounts (manager only) */
	getAll() {
		return axiosInstance.get<CompteDto[]>("/comptes/all");
	},

	/** GET /comptes/client/{id}/all – retrieve all accounts for a given client */
	getByClient(clientId: string) {
		return axiosInstance.get<CompteDto[]>(`/comptes/client/${clientId}/all`);
	},

	/** GET /comptes/client/{compteId} – retrieve a single account for the authenticated client */
	getOne(compteId: string) {
		return axiosInstance.get<CompteDto>(`/comptes/client/${compteId}`);
	},
};

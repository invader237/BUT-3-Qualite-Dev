import { createContext, useContext } from "react";
import type { CompteDto, UtilisateurDto } from "@/types";

export interface UserWithComptes {
	utilisateur: UtilisateurDto;
	comptes: CompteDto[];
}

export interface UserListContextValue {
	users: UserWithComptes[];
}

export const UserListContext = createContext<UserListContextValue | null>(null);

export function useUserListContext(): UserListContextValue {
	const ctx = useContext(UserListContext);
	if (!ctx) {
		throw new Error("UserList compound components must be used within <UserList.Root>");
	}
	return ctx;
}

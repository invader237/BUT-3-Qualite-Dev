import { createContext, useContext } from "react";

export type CompteListVariant = "user" | "admin";

export interface CompteListContextValue {
	variant: CompteListVariant;
}

export const CompteListContext = createContext<CompteListContextValue | null>(null);

export function useCompteListContext(): CompteListContextValue {
	const ctx = useContext(CompteListContext);
	if (!ctx) {
		throw new Error("CompteList compound components must be used within <CompteList.Root>");
	}
	return ctx;
}

import { createContext, useContext } from "react";

export interface SidebarContextValue {
	collapsed: boolean;
	toggle: () => void;
	expand: () => void;
	collapse: () => void;
}

export const SidebarContext = createContext<SidebarContextValue | null>(null);

export function useSidebarContext(): SidebarContextValue {
	const ctx = useContext(SidebarContext);
	if (!ctx) {
		throw new Error("Sidebar compound components must be used within <Sidebar.Root>");
	}
	return ctx;
}

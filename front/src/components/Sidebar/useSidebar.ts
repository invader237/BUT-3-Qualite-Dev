import { useState, useCallback } from "react";

export interface UseSidebarReturn {
	collapsed: boolean;
	toggle: () => void;
	expand: () => void;
	collapse: () => void;
}

/**
 * Hook to manage sidebar collapsed/expanded state.
 * Can be used standalone or passed into <Sidebar.Root>.
 */
export function useSidebar(defaultCollapsed = false): UseSidebarReturn {
	const [collapsed, setCollapsed] = useState(defaultCollapsed);

	const toggle = useCallback(() => setCollapsed((c) => !c), []);
	const expand = useCallback(() => setCollapsed(false), []);
	const collapse = useCallback(() => setCollapsed(true), []);

	return { collapsed, toggle, expand, collapse };
}

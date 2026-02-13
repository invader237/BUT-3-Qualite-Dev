import { useEffect, useMemo, useState } from "react";
import { Users } from "lucide-react";
import { Spinner, Text } from "@radix-ui/themes";
import { CompteList } from "@/components/CompteList";
import { compteApi } from "@/api";
import type { CompteDto } from "@/types";

export function AdminComptesPage() {
	const [comptes, setComptes] = useState<CompteDto[]>([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState<string | null>(null);

	useEffect(() => {
		let cancelled = false;

		compteApi
			.getAll()
			.then(({ data }) => {
				if (!cancelled) setComptes(data);
			})
			.catch(() => {
				if (!cancelled) setError("Impossible de charger les comptes.");
			})
			.finally(() => {
				if (!cancelled) setLoading(false);
			});

		return () => { cancelled = true; };
	}, []);

	const grouped = useMemo(() => {
		const map = new Map<string, CompteDto[]>();
		for (const compte of comptes) {
			const list = map.get(compte.clientId) ?? [];
			list.push(compte);
			map.set(compte.clientId, list);
		}
		return map;
	}, [comptes]);

	if (loading) {
		return <Spinner size="3" />;
	}

	if (error) {
		return <Text color="red">{error}</Text>;
	}

	return (
		<CompteList.Root variant="admin">
			<CompteList.Header
				icon={Users}
				title="Gestion des comptes"
				description="Vue d'ensemble des comptes par utilisateur."
			/>
			<CompteList.Content>
				{comptes.length === 0
					? <CompteList.Empty message="Aucun compte enregistré." />
					: Array.from(grouped.entries()).map(([clientId, clientComptes]) => (
						<CompteList.Group key={clientId} label={clientId}>
							{clientComptes.map((compte) => (
								<CompteList.Item key={compte.id} compte={compte} />
							))}
						</CompteList.Group>
					))
				}
			</CompteList.Content>
		</CompteList.Root>
	);
}

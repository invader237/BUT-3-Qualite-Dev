import { useEffect, useState } from "react";
import { Landmark } from "lucide-react";
import { Spinner, Text } from "@radix-ui/themes";
import { CompteList } from "@/components/CompteList";
import { compteApi } from "@/api";
import { useAuth } from "@/auth";
import type { CompteDto } from "@/types";

export function MesComptesPage() {
	const { user } = useAuth();
	const [comptes, setComptes] = useState<CompteDto[]>([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState<string | null>(null);

	useEffect(() => {
		if (!user) return;

		let cancelled = false;

		compteApi
			.getByClient(user.userId)
			.then(({ data }) => {
				if (!cancelled) setComptes(data);
			})
			.catch(() => {
				if (!cancelled) setError("Impossible de charger vos comptes.");
			})
			.finally(() => {
				if (!cancelled) setLoading(false);
			});

		return () => { cancelled = true; };
	}, [user]);

	if (loading) {
		return <Spinner size="3" />;
	}

	if (error) {
		return <Text color="red">{error}</Text>;
	}

	return (
		<CompteList.Root variant="user">
			<CompteList.Header
				icon={Landmark}
				title="Mes comptes"
				description="Retrouvez ici l'ensemble de vos comptes bancaires."
			/>
			<CompteList.Content>
				{comptes.length === 0
					? <CompteList.Empty />
					: comptes.map((compte) => (
						<CompteList.Item key={compte.id} compte={compte} />
					))
				}
			</CompteList.Content>
		</CompteList.Root>
	);
}

import { useEffect, useState } from "react";
import { Users } from "lucide-react";
import { Spinner, Text } from "@radix-ui/themes";
import { UserList } from "@/components/UserList";
import type { UserWithComptes } from "@/components/UserList";
import { compteApi, userApi } from "@/api";
import type { CompteDto, UtilisateurDto } from "@/types";

export function AdminUtilisateursPage() {
	const [data, setData] = useState<UserWithComptes[]>([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState<string | null>(null);

	useEffect(() => {
		let cancelled = false;

		async function fetchData() {
			try {
				const { data: comptes } = await compteApi.getAll();

				const clientIds = [...new Set(comptes.map((c: CompteDto) => c.clientId))];

				const users = await Promise.all(
					clientIds.map(async (clientId): Promise<UserWithComptes> => {
						let utilisateur: UtilisateurDto;
						try {
							const res = await userApi.getUtilisateur(clientId);
							utilisateur = res.data;
						} catch {
							utilisateur = {
								userId: clientId,
								nom: clientId,
								prenom: "",
								male: true,
								type: "CLIENT",
							};
						}
						return {
							utilisateur,
							comptes: comptes.filter((c: CompteDto) => c.clientId === clientId),
						};
					})
				);

				if (!cancelled) setData(users);
			} catch {
				if (!cancelled) setError("Impossible de charger les utilisateurs.");
			} finally {
				if (!cancelled) setLoading(false);
			}
		}

		fetchData();
		return () => { cancelled = true; };
	}, []);

	if (loading) {
		return <Spinner size="3" />;
	}

	if (error) {
		return <Text color="red">{error}</Text>;
	}

	return (
		<UserList.Root users={data}>
			<UserList.Header
				icon={Users}
				title="Utilisateurs"
				description="Liste des utilisateurs et de leurs comptes associés."
			/>
			<UserList.Content>
				{data.length === 0
					? <UserList.Empty />
					: data.map((entry) => (
						<UserList.UserCard key={entry.utilisateur.userId} user={entry}>
							{entry.comptes.length === 0
								? <Text size="2" color="gray">Aucun compte associé.</Text>
								: entry.comptes.map((compte) => (
									<UserList.AccountItem key={compte.id} compte={compte} />
								))
							}
						</UserList.UserCard>
					))
				}
			</UserList.Content>
		</UserList.Root>
	);
}

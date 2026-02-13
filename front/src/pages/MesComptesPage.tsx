import { Flex, Text, Card } from "@radix-ui/themes";
import { Landmark } from "lucide-react";

export function MesComptesPage() {
	return (
		<Flex direction="column" gap="4">
			<Flex align="center" gap="2">
				<Landmark size={24} />
				<Text size="5" weight="bold">
					Mes comptes
				</Text>
			</Flex>

			<Text color="gray">Retrouvez ici l'ensemble de vos comptes bancaires.</Text>

			<Flex direction="column" gap="3" style={{ maxWidth: 600 }}>
				<Card>
					<Flex justify="between" align="center">
						<Flex direction="column" gap="1">
							<Text weight="medium">Compte courant</Text>
							<Text size="2" color="gray">FR76 1234 5678 9012 3456 7890 123</Text>
						</Flex>
						<Text size="5" weight="bold">1 245,80 €</Text>
					</Flex>
				</Card>

				<Card>
					<Flex justify="between" align="center">
						<Flex direction="column" gap="1">
							<Text weight="medium">Livret A</Text>
							<Text size="2" color="gray">FR76 9876 5432 1098 7654 3210 987</Text>
						</Flex>
						<Text size="5" weight="bold">8 320,00 €</Text>
					</Flex>
				</Card>
			</Flex>
		</Flex>
	);
}

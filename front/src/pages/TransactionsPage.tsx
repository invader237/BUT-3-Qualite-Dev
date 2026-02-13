import { Flex, Text, Table } from "@radix-ui/themes";
import { CreditCard } from "lucide-react";

const MOCK_TRANSACTIONS = [
	{ id: 1, date: "2026-02-12", label: "Courses supermarché", montant: -67.3 },
	{ id: 2, date: "2026-02-11", label: "Salaire", montant: 2150.0 },
	{ id: 3, date: "2026-02-10", label: "Restaurant", montant: -42.5 },
	{ id: 4, date: "2026-02-09", label: "Abonnement streaming", montant: -13.99 },
	{ id: 5, date: "2026-02-08", label: "Virement reçu", montant: 150.0 },
];

export function TransactionsPage() {
	return (
		<Flex direction="column" gap="4">
			<Flex align="center" gap="2">
				<CreditCard size={24} />
				<Text size="5" weight="bold">
					Transactions
				</Text>
			</Flex>

			<Text color="gray">Historique de vos dernières transactions.</Text>

			<Table.Root variant="surface">
				<Table.Header>
					<Table.Row>
						<Table.ColumnHeaderCell>Date</Table.ColumnHeaderCell>
						<Table.ColumnHeaderCell>Libellé</Table.ColumnHeaderCell>
						<Table.ColumnHeaderCell align="right">Montant</Table.ColumnHeaderCell>
					</Table.Row>
				</Table.Header>
				<Table.Body>
					{MOCK_TRANSACTIONS.map((tx) => (
						<Table.Row key={tx.id}>
							<Table.Cell>{tx.date}</Table.Cell>
							<Table.Cell>{tx.label}</Table.Cell>
							<Table.Cell align="right">
								<Text color={tx.montant >= 0 ? "green" : "red"} weight="medium">
									{tx.montant >= 0 ? "+" : ""}
									{tx.montant.toFixed(2)} €
								</Text>
							</Table.Cell>
						</Table.Row>
					))}
				</Table.Body>
			</Table.Root>
		</Flex>
	);
}

import React from "react";
import { Flex, Text, Card, Badge, Avatar } from "@radix-ui/themes";
import { type LucideIcon } from "lucide-react";
import { UserListContext, type UserWithComptes } from "./UserListContext";
import type { CompteDto } from "@/types";
import "./UserList.css";

/* ------------------------------------------------------------------ */
/*  UserList.Root                                                      */
/* ------------------------------------------------------------------ */

interface RootProps {
	children: React.ReactNode;
	users: UserWithComptes[];
}

function Root({ children, users }: RootProps) {
	return (
		<UserListContext.Provider value={{ users }}>
			<div className="user-list">{children}</div>
		</UserListContext.Provider>
	);
}

/* ------------------------------------------------------------------ */
/*  UserList.Header – title section with icon                          */
/* ------------------------------------------------------------------ */

interface HeaderProps {
	icon: LucideIcon;
	title: string;
	description?: string;
}

function Header({ icon: Icon, title, description }: HeaderProps) {
	return (
		<div className="user-list__header">
			<Flex align="center" gap="2">
				<Icon size={24} />
				<Text size="5" weight="bold">{title}</Text>
			</Flex>
			{description && <Text color="gray">{description}</Text>}
		</div>
	);
}

/* ------------------------------------------------------------------ */
/*  UserList.Content – container for user cards                        */
/* ------------------------------------------------------------------ */

interface ContentProps {
	children: React.ReactNode;
}

function Content({ children }: ContentProps) {
	return <div className="user-list__content">{children}</div>;
}

/* ------------------------------------------------------------------ */
/*  UserList.UserCard – card for a single user with their accounts     */
/* ------------------------------------------------------------------ */

interface UserCardProps {
	user: UserWithComptes;
	children: React.ReactNode;
}

function UserCard({ user, children }: UserCardProps) {
	const { utilisateur } = user;

	const initials = `${utilisateur.prenom[0]}${utilisateur.nom[0]}`.toUpperCase();

	return (
		<Card className="user-list__user-card">
			<Flex direction="column" gap="3">
				<Flex align="center" gap="3">
					<Avatar size="3" radius="full" fallback={initials} />
					<Flex direction="column" gap="0">
						<Flex align="center" gap="2">
							<Text weight="bold">{utilisateur.prenom} {utilisateur.nom}</Text>
							<Badge size="1" variant="soft" color="gray">{utilisateur.type}</Badge>
						</Flex>
						<Text size="2" color="gray">{utilisateur.userId}</Text>
						{utilisateur.adresse && (
							<Text size="1" color="gray">{utilisateur.adresse}</Text>
						)}
					</Flex>
				</Flex>
				<div className="user-list__accounts">{children}</div>
			</Flex>
		</Card>
	);
}

/* ------------------------------------------------------------------ */
/*  UserList.AccountItem – single account line within a user card      */
/* ------------------------------------------------------------------ */

interface AccountItemProps {
	compte: CompteDto;
}

function formatCurrency(amount: number): string {
	return new Intl.NumberFormat("fr-FR", {
		style: "currency",
		currency: "EUR",
	}).format(amount);
}

function AccountItem({ compte }: AccountItemProps) {
	const hasDecouvert = compte.decouvertAutorise > 0;

	return (
		<div className="user-list__account-item">
			<Flex justify="between" align="center">
				<Flex align="center" gap="2">
					<Text size="2" weight="medium">{compte.id}</Text>
					{hasDecouvert && (
						<Badge color="blue" variant="soft" size="1">Découvert</Badge>
					)}
				</Flex>
				<Text
					size="2"
					weight="bold"
					color={compte.solde < 0 ? "red" : undefined}
				>
					{formatCurrency(compte.solde)}
				</Text>
			</Flex>
		</div>
	);
}

/* ------------------------------------------------------------------ */
/*  UserList.Empty – placeholder when no users                         */
/* ------------------------------------------------------------------ */

interface EmptyProps {
	message?: string;
}

function Empty({ message = "Aucun utilisateur trouvé." }: EmptyProps) {
	return (
		<div className="user-list__empty">
			<Text color="gray">{message}</Text>
		</div>
	);
}

/* ------------------------------------------------------------------ */
/*  Compound export                                                    */
/* ------------------------------------------------------------------ */

export const UserList = {
	Root,
	Header,
	Content,
	UserCard,
	AccountItem,
	Empty,
};

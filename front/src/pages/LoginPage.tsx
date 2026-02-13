import { useState, type FormEvent } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Button, Flex, Text, TextField, Card, Callout } from "@radix-ui/themes";
import { LogIn, AlertCircle } from "lucide-react";
import { useLogin } from "@/hooks";
import { useAuth } from "@/auth";
import type { LoginRequest } from "@/types";

/**
 * Login page – shown when /me returns 401.
 * Uses the useLogin hook to call POST /auth/login, then re-fetches /me.
 */
export function LoginPage() {
	const navigate = useNavigate();
	const location = useLocation();
	const { refresh } = useAuth();
	const { login, loading, error } = useLogin();

	const [userId, setUserId] = useState("");
	const [password, setPassword] = useState("");

	// Where to redirect after successful login
	const from = (location.state as { from?: { pathname: string } })?.from?.pathname ?? "/";

	async function handleSubmit(e: FormEvent) {
		e.preventDefault();

		try {
			const payload: LoginRequest = { userId, password };
			await login(payload);
			await refresh();
			navigate(from, { replace: true });
		} catch {
			// error is already set inside useLogin
		}
	}

	return (
		<Flex
			align="center"
			justify="center"
			style={{ minHeight: "100vh", padding: 16, background: "var(--color-background)" }}
		>
			<Card size="3" style={{ width: "100%", maxWidth: 400 }}>
				<form onSubmit={handleSubmit}>
					<Flex direction="column" gap="4">
						<Flex align="center" gap="2">
							<LogIn size={22} />
							<Text size="5" weight="bold">
								Connexion
							</Text>
						</Flex>

						{error && (
							<Callout.Root color="red" size="1">
								<Callout.Icon>
									<AlertCircle size={16} />
								</Callout.Icon>
								<Callout.Text>{error}</Callout.Text>
							</Callout.Root>
						)}

						<label>
							<Text size="2" weight="medium" mb="1" as="p">
								Identifiant
							</Text>
							<TextField.Root
								type="text"
								placeholder="votre identifiant"
								value={userId}
								onChange={(e) => setUserId(e.target.value)}
								required
							/>
						</label>

						<label>
							<Text size="2" weight="medium" mb="1" as="p">
								Mot de passe
							</Text>
							<TextField.Root
								type="password"
								placeholder="••••••••"
								value={password}
								onChange={(e) => setPassword(e.target.value)}
								required
							/>
						</label>

						<Button type="submit" disabled={loading} style={{ marginTop: 8 }}>
							{loading ? "Connexion…" : "Se connecter"}
						</Button>
					</Flex>
				</form>
			</Card>
		</Flex>
	);
}

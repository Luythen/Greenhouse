import { createContext, useCallback, useContext, useEffect, useMemo, useState } from "react"
import type { LoginInterface } from "../interface/LoginInterface";

interface AuthContextInterface {
    accessToken: string | null,
    firstName: string | null,
    lastName: string | null,
    loading: boolean,
    login: (accessToken: string, firstName: string, lastName: string) => void,
    logout: () => void
}

const AuthContext = createContext<AuthContextInterface | null>(null);


export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [accessToken, setAccessToken] = useState<string | null>(null);
    const [firstName, setFirstName] = useState<string | null>(null);
    const [lastName, setLastName] = useState<string | null>(null);

    const [loading, setLoading] = useState<boolean>(true);

    const api = import.meta.env.VITE_API_URL ?? "";

    useEffect(() => {(async () => {
        try {
            const res = await fetch(api + "/auth/refresh", {
                method: "POST",
                credentials: "include"
            })
            if (res.ok) {
                const data = await res.json() as LoginInterface;
                setAccessToken(data.accessToken);
                setFirstName(data.firstName);
                setLastName(data.lastName);
            }
        } finally {
            setLoading(false);
        }
    })()}, [])

    const login = useCallback((accessToken: string, firstName: string, lastName: string) => {
        setAccessToken(accessToken);
        setFirstName(firstName);
        setLastName(lastName);
    }, [])

    const logout = useCallback(() => {
        setAccessToken(null);
        setFirstName(null);
        setLastName(null);
    }, [])

    const value = useMemo(() => ({
        accessToken, firstName, lastName, loading, login, logout
    }), [accessToken, firstName, lastName, loading, login, logout])

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuth = () => {
    const ctx = useContext(AuthContext);
    if (!ctx) throw new Error("problem");
    return ctx;
}
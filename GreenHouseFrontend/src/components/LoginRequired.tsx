import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext"

export const LoginRequired = () => {
    const { accessToken, loading } = useAuth();

    if (loading) return <div>Loading...</div>
    if (!accessToken) return <Navigate to={"/login"} />

    return <Outlet />
}
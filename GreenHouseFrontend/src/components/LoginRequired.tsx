import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext"

export const LoginRequired = () => {
    const { accessToken, loading } = useAuth();

    if (loading) return <div className="spinner-border" style={{width: "3rem", height: "3rem"}} role="status"><span className="visually-hidden">Loading…</span></div>
    if (!accessToken) return <Navigate to={"/login"} />

    return <Outlet />
}
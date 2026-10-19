import { useState } from "react";
import { useAuth } from "../context/AuthContext"
import type { LoginInterface } from "../interface/LoginInterface";

export const Login = () => {
    const [email, setEmail] = useState<string | null>(null);
    const [password, setPassword] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const { login } = useAuth();

    const handleLogin = async() => {
        setLoading(true);
        const res = await fetch("http://localhost:5050/auth/login", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email,
                password
            })
        })
        if (res.ok) {
            const data = await res.json() as LoginInterface;
            login(data.accessToken, data.firstName, data.lastName);
            window.location.href = "/greenhouse"
        }
    }

    return (
        <div className="container">
            { loading ? <div>Laddar</div> : 
            <div className="card">
                <div className="card-body">
                    <div className="form-field">
                        <label>Email</label>
                        <input type="email" className="form-control" onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div className="form-field">
                        <label>Lösenord</label>
                        <input type="password" className="form-control" onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <button className="theme-primary btn-subtle rounded-0" onClick={handleLogin}>Logga in</button>
                </div>
            </div>}
        </div>
    )
}
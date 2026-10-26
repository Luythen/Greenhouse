import { useState } from "react"
import { useAuth } from "../context/AuthContext";

interface ApiKey {
    apiKey: string
}

export const Key = () => {
    const [key, setKey] = useState<string | undefined>(undefined);
    const { accessToken } = useAuth();

    const api = import.meta.env.VITE_API_URL ?? "";

    const handleGenerateKey = async () => {
        const res = await fetch(api + "/key/generate", {
            method: "POST",
            credentials: "include",
            headers: {
                "Authorization": "Bearer " + accessToken
            }
        })

        if (res.ok) {
            const data = await res.json() as ApiKey;
            setKey(data.apiKey);
        }
    }

    return (
        <div className="form-field">
            <div className="card card-subtle theme-danger">
                <div className="card-header">
                    Kom ihåg
                </div>
                <div className="card-body">
                    <p className="card-text">När du skapar en ny api nyckel så <b>måste du</b> spara nyckeln för du kommer ej få tillbaka samma nyckel!</p>
                </div>
            </div>
            <textarea className="form-control" rows={4} value={key} />
            <button className="theme-primary btn-subtle rounded-0" onClick={handleGenerateKey}>Skapa</button>
        </div>
    )
}
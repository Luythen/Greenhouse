import { useState } from "react"

export const Register = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const [firstName, setFirstName] = useState<string |null>(null);
    const [lastName, setLastName] = useState<string |null>(null);
    const [email, setEmail] = useState<string |null>(null);
    const [password, setPassword] = useState<string |null>(null);

    const api = import.meta.env.VITE_API_URL ?? "";
    
    const handleRegister = async () => {
        setLoading(true)
        try {
            const res = await fetch(api + "/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    firstName,
                    lastName,
                    email,
                    password
                })
            })

            if (res.ok) {
                const data = await res.json();
                console.log(data)
            }

        } finally {
            
        }
    }

    return (
        <div className="container">
            { loading ? <div>loading</div> : 
            <div className="card">
                <div className="card-body">
                    <div className="form-field">
                        <label>Förnamn</label>
                        <input type="text" className="form-control" onChange={(e) => setFirstName(e.target.value)} />
                    </div>
                    <div className="form-field">
                        <label>Efternamn</label>
                        <input type="text" className="form-control" onChange={(e) => setLastName(e.target.value)} />
                    </div>
                    <div className="form-field">
                        <label>Email</label>
                        <input type="email" className="form-control" onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div className="form-field">
                        <label>Lösenord</label>
                        <input type="password" className="form-control" onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <button className="theme-primary btn-subtle rounded-0" onClick={handleRegister}>Register</button>
                </div>
            </div>}
        </div>
    )
}
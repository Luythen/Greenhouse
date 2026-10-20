import { useState } from "react";
import type { Crate } from "../interface/GreenHouseInterface";

export const CreatePlantCrate = ({ accessToken, updateCrates }: { accessToken: string, updateCrates: (crate: Crate) => void}) => {

    const [label, setLabel] = useState<string | null>(null);
    const [sensor, setSensor] = useState<string | null>(null)

    const handleAddCrate = async () => {
        if (label === null) return;
        const res = await fetch("http://localhost:5050/plantcrate/add", {
            method: "POST",
            credentials: "include",
            body: JSON.stringify({ label, sensorId: sensor}),
            headers: {
                "Authorization": "Bearer " + accessToken,
                "Content-Type": "application/json"
            }
        })
    
        if (res.ok) {
            const data = await res.json() as Crate;
            updateCrates(data);
        }
    }

    return (
        <div>
            <button className="theme-primary btn-subtle rounded-0" type="button" data-bs-toggle="collapse" data-bs-target="#crateCollapse" aria-expanded="false" aria-controls="crateCollapse">Skapa ny låda</button>
            <div className="collapse" id="crateCollapse" style={{paddingTop: "10px"}}>
                <div className="card card-body">
                    <div className="form-field">
                        <label>Namn</label>
                        <input type="text" className="form-control" onChange={(e) => setLabel(e.target.value)} />
                    </div>
                    <div className="form-field">
                        <label>Sensor id</label>
                        <input type="text" className="form-control" onChange={(e) => setSensor(e.target.value)} />
                    </div>
                    <button className="theme-primary btn-subtle rounded-0" onClick={handleAddCrate}>Skapa</button>
                </div>
            </div>
        </div>
    )
}
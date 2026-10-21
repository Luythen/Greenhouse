import { useState } from "react";
import type { Crate } from "../interface/GreenHouseInterface";
import { Plant } from "./Plant";

export const PlantCrate = ({ crate, removeCrate, updateCrate,accessToken }: { crate: Crate, removeCrate: (removeCrate: Crate) => void, updateCrate: (uCrate: Crate) => void, accessToken: string }) => {
    const [edit, setEdit] = useState<boolean>(false);

    const [name, setName] = useState<string | null>(null);
    const [sensor, setSensor] = useState<string | null>(null);

    const handleDelete = async () => {
        const res = await fetch("http://localhost:5050/plantcrate/remove/" + crate.id, {
            method: "POST",
            credentials: "include",
            headers: {
                "Authorization": "Bearer " + accessToken
            }
        })

        if (res.ok) {
            removeCrate(crate);
        }
    }

    const handleEdit = async () => {
        if (name !== null || sensor !== null) {
            const res = await fetch("http://localhost:5050/plantcrate/edit/" + crate.id, {
                method: "PUT",
                credentials: "include",
                headers: {
                    "Authorization": "Bearer " + accessToken,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ 
                    label: name !== null ? name : crate.label,
                    sensorId: sensor !== null ? sensor : crate.moistureSensorConnectionId
                })
            })

            if (res.ok) {
                const data = await res.json() as Crate
                updateCrate(data)
            }
        }

        setEdit(false);
    }

    return (
        <div className="col">
            <div className="card">
                <div className="card-body">
                    { edit ? <div className="form-field">
                        <label>Namn</label>
                        <input className="form-control" placeholder={crate.label} type="text" onChange={(e) => setName(e.target.value)} />
                    </div> : <h4 className="card-title">{crate.label}</h4> }
                    <Plant crate={crate} accessToken={accessToken} />
                </div>
                <div className="card-body">
                    { edit ? <div className="form-field">
                        <label>Sensor</label>
                        <input className="form-control" type="text" placeholder={crate.moistureSensorConnectionId} onChange={(e) => setSensor(e.target.value)} />
                    </div> : <>{ crate.moistureSensorConnectionId ?? "Ingen sensor ansluten" }</>}
                </div>
                <div className="card-footer">
                    <button className="btn btn-icon theme-danger btn-subtle rounded-0" aria-label="Icon Only" onClick={handleDelete}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash-fill" viewBox="0 0 16 16">
                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                        </svg>
                    </button>
                    { edit ? <button type="button" className="theme-primary btn-subtle rounded-0" onClick={() => handleEdit()}>
                        Spara
                    </button> : <button type="button" className="theme-primary btn-subtle rounded-0" onClick={() => setEdit(true)}>
                        Redigera
                    </button> }
                </div>
            </div>
        </div>
    )
}
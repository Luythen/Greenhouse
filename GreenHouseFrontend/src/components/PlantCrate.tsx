import type { Crate } from "../interface/GreenHouseInterface";
import { Plant } from "./Plant";

export const PlantCrate = ({ crate, removeCrate, accessToken }: { crate: Crate, removeCrate: (removeCrate: Crate) => void, accessToken: string }) => {

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

    return (
        <div className="col">
            <div className="card">
                <div className="card-body">
                    <h4 className="card-title">{crate.label}</h4>
                    <Plant crate={crate} accessToken={accessToken} />
                </div>
                <div className="card-body">
                    { crate.moistureSensorConnectionId ?? "Ingen sensor ansluten" }
                </div>
                <div className="card-footer">
                    <button className="btn btn-icon theme-danger btn-subtle rounded-0" aria-label="Icon Only" onClick={handleDelete}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash-fill" viewBox="0 0 16 16">
                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                        </svg>
                    </button>
                </div>
            </div>
        </div>
    )
}
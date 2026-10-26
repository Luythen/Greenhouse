import { useState } from "react"
import type { Crate, PlantInterface, PlantType } from "../interface/GreenHouseInterface"
import { PlantTypes } from "../PlantType"

export const Plant = ({ crate, accessToken }: { crate: Crate, accessToken: string }) => {
    const [plantHistory, setPlantHistory] = useState<PlantInterface[]>(crate.plants.filter((p) => !p.active ))
    const [activePlant, setActivePlant] = useState<PlantInterface | null>(crate.plants.filter((p) => p.active)[0] ?? null)

    const api = import.meta.env.VITE_API_URL ?? "";

    const handlePlant = async (plantType: string) => {
        const res = await fetch(api + "/plant/vegetable", {
            method: "POST",
            credentials: "include",
            headers: {
                "Authorization": "Bearer " + accessToken,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ crateId: crate.id, plantType })
        })

        if (res.ok) {
            const data = await res.json() as PlantInterface;
            setActivePlant(data);
        }
    }

    const handleRemovePlant = async () => {
        const res = await fetch(api + "/plant/unplant/" + activePlant?.id, {
            method: "PATCH",
            credentials: "include",
            headers: {
                "Authorization": "Bearer " + accessToken
            },
        })

        if (res.ok) {
            setPlantHistory([...plantHistory, activePlant!])
            setActivePlant(null);
        }
    }

    const handlePlantStatus = (plant: PlantType) => {
        return plantHistory.some((ph) => ph.plantType === plant.PlantType) && plantHistory.filter((ph) => (new Date(ph.plantedAt).getFullYear() + 4) <= new Date(Date.now()).getFullYear()).length === 0
    }

    const plantAgainDate = (plant: PlantType) => {
        if (plantHistory.some((ph) => ph.plantType === plant.PlantType)) {
            return new Date(plantHistory.filter(((ph) => ph.plantType === plant.PlantType))[0]?.plantedAt).getFullYear() + 4;
        }
    }

    return (
        <div className="d-grid gap-2">
            { activePlant == null ? 
            <>
                <button type="button" className="theme-secondary btn-subtle btn-lg rounded-0" data-bs-toggle="menu" aria-expanded="false">Plantera</button>
                <div className="menu">
                    { PlantTypes.map((pt) => ( <a className={handlePlantStatus(pt) ? "menu-item disabled" : "menu-item"} onClick={() => handlePlant(pt.PlantType)} key={pt.PlantType}><div className="menu-item-content">
                       {pt.PlantName}
                        <small className="menu-item-description">{ plantAgainDate(pt) ?? "" }</small>
                    </div></a>)) }
                </div>
            </> : 
            <>
                <button type="button" className="theme-success btn-subtle btn-lg rounded-0" data-bs-toggle="menu" aria-expanded="false">{ PlantTypes.filter((pt) => pt.PlantType === activePlant.plantType)[0].PlantName }</button>
                <div className="menu">
                    <button className="menu-item theme-danger" type="button" onClick={handleRemovePlant}>Rensa</button>
                </div>
            </> }
        </div>
    )
}
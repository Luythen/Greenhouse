import { useCallback, useEffect, useState } from "react"
import type { Crate, Greenhouse } from "../interface/GreenHouseInterface";
import { useAuth } from "../context/AuthContext";
import { CreatePlantCrate } from "./CreatePlantCrate";
import { PlantCrate } from "./PlantCrate";
import { useWebSocket } from "../context/WebSocketContext";
import type { Soil } from "../interface/WebSocketInterface";

export const GreenHouse = () => {
    const [loading, setLoading] = useState<boolean>(true);

    const [greenHouseName, SetGreenHouseName] = useState<string | null>(null);
    const [greenHouseId, SetGreenHouseId] = useState<string | null>(null);
    const [crates, setCrates] = useState<Crate[]>([]);
    const [soilData, setSoilData] = useState<Soil[] | null>(null);
    
    const { accessToken } = useAuth();

    const api = import.meta.env.VITE_API_URL ?? "";

    const client = useWebSocket();

    useEffect(() => {(async() => {
        try {
            const res = await fetch(api + "/greenhouse/dashboard", {
                method: "GET",
                credentials: "include",
                headers: {
                    "Authorization": "Bearer " + accessToken
                }
            })

            if (res.ok) {
                const data = await res.json() as Greenhouse;
                SetGreenHouseName(data.name);
                SetGreenHouseId(data.id);
                setCrates(data.crates);
            }
        } finally {
            setLoading(false);
        }
    })()}, [])

    useEffect(() => {
        if (!client) return;

        const sub = client.subscribe("/topic/soil/" + greenHouseId, (message) => {
            const parsed: Soil[] = JSON.parse(message.body);
            setSoilData(parsed);
        })

        return () => {
            sub.unsubscribe();
        }
    }, [client])

    /** https://dev.to/andyrewlee/how-to-update-an-array-of-objects-in-react-state-3d */
    const updateCrates = useCallback((crate: Crate) => {
        const newCrateList = crates.map((c) => {
            if (c.id === crate.id) {
                return crate;
            }

            return c;
        })

        const exits = crates.some(c => c.id === crate.id);

        setCrates(exits ? newCrateList : [...crates, crate]);
    }, [crates])

    const removeCrate = useCallback((crate: Crate) => {
        const newCrateList = crates.filter((c) => c.id !== crate.id)

        setCrates(newCrateList);
    }, [crates])

    return (
        <div className="container">
            { loading ? <h1>Loading</h1> :
            <div>
                <h1 className="text-center">{ greenHouseName }</h1>
                <CreatePlantCrate accessToken={accessToken!} updateCrates={updateCrates} />
                <div className="row" style={{paddingTop: "20px"}}>
                    { crates.length < 0 ? <div>Inga lådor</div> : crates.map(c => (
                        <PlantCrate soilData={soilData?.some((s) => s.moistureSensorId === c.moistureSensorConnectionId) ? soilData?.filter((s) => s.moistureSensorId === c.moistureSensorConnectionId)[0]! : null} accessToken={accessToken!} updateCrate={updateCrates} removeCrate={removeCrate} key={c.id} crate={c} />
                        )) }
                </div>
            </div>}
        </div>
    )
}
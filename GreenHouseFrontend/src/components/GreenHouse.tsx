import { useCallback, useEffect, useState } from "react"
import type { Crate, Greenhouse } from "../interface/GreenHouseInterface";
import { useAuth } from "../context/AuthContext";
import { CreatePlantCrate } from "./CreatePlantCrate";
import { PlantCrate } from "./PlantCrate";

export const GreenHouse = () => {
    const [loading, setLoading] = useState<boolean>(true);

    const [greenHouseName, SetGreenHouseName] = useState<string | null>(null);
    const [crates, setCrates] = useState<Crate[]>([]);
    const { accessToken } = useAuth();

    useEffect(() => {(async() => {
        try {
            const res = await fetch("http://localhost:5050/greenhouse/dashboard", {
                method: "GET",
                credentials: "include",
                headers: {
                    "Authorization": "Bearer " + accessToken
                }
            })

            if (res.ok) {
                const data = await res.json() as Greenhouse;
                SetGreenHouseName(data.name);
                setCrates(data.crates);
            }
        } finally {
            setLoading(false);
        }
    })()}, [])

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

    return (
        <div className="container">
            { loading ? <h1>Loading</h1> :
            <div>
                <h1 className="text-center">{ greenHouseName }</h1>
                <CreatePlantCrate accessToken={accessToken!} updateCrates={updateCrates} />
                <div className="row">
                    { crates.length < 0 ? <div>Inga lådor</div> : crates.map(c => (<PlantCrate crate={c} />)) }
                </div>
            </div>}
        </div>
    )
}
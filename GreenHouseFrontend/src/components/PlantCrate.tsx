import type { Crate } from "../interface/GreenHouseInterface";

export const PlantCrate = ({ crate }: { crate: Crate }) => {
    return (
        <div className="col">
            <div className="card">
                <div className="card-body">
                    <h4 className="card-title">{crate.label}</h4>
                    <p>Plant</p>
                </div>
                <div className="card-body">
                    { crate.moistureSensorConnectionId ?? "Ingen sensor ansluten" }
                </div>
            </div>
        </div>
    )
}
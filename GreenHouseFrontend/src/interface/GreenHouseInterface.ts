export interface Crate {
    id: string,
    label: string,
    moistureSensorConnectionId: string,
    plants: PlantInterface[]    
}

export interface Greenhouse {
    id: string,
    name: string,
    crates: Crate[]
}

export interface PlantInterface {
    id: string,
    plantType: string,
    plantedAt: Date,
    removedAt: Date,
    active: boolean
}

export interface PlantType {
    PlantType: string,
    PlantName: string
}
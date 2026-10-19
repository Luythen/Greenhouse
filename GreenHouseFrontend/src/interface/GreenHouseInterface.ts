export interface Crate {
    id: string,
    label: string,
    moistureSensorConnectionId: string,
    plants: []     
}

export interface Greenhouse {
    id: string,
    name: string,
    crates: Crate[]
}
import { useAuth } from "../context/AuthContext"
import { Key } from "./Key"

export const Settings = () => {
    const { firstName, lastName } = useAuth();
    return (
        <div className="container">
            <div className="card" style={{ marginLeft: "11.5rem", marginRight: "11.5rem", marginTop: "2.5rem", height: "50%"}}>
                <div className="card-body">
                    <div className="row">
                        <div className="col-4">
                            <div className="list-group" id="list-tab" role="tablist">
                                <a className="list-group-item list-group-item-action active" id="list-home-list" data-bs-toggle="tab" href="#list-home" role="tab" aria-controls="list-home">Hem</a>
                                <a className="list-group-item list-group-item-action" id="list-key-list" data-bs-toggle="tab" href="#list-key" role="tab" aria-controls="list-key">Nycklar</a>
                            </div>
                        </div>
                        <div className="col-8">
                            <div className="tab-content" id="list-tabContent">
                                <div className="tab-pane show active" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
                                    <input className="form-control" value={firstName!} disabled />
                                    <input className="form-control" value={lastName!} disabled />
                                </div>
                                <div className="tab-pane" id="list-key" role="tabpanel" aria-labelledby="list-key-list">
                                    <Key />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
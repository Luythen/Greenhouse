import { useAuth } from "../context/AuthContext"

export const Navbar = ({ children }: { children: React.ReactNode }) => {
    const { accessToken } = useAuth()

    return (
        <>
        <nav className="navbar sm:navbar-expand bg-1 fg-2">
            <div className="container-fluid">
                <a className="navbar-brand">Solr</a>
                <dialog className="drawer drawer-end" tabIndex={-1}>
                    <div className="drawer-body">
                        { accessToken !== null ? 
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <a className="nav-link" href="/greenhouse">Växthus</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/settings">Inställningar</a>
                            </li>
                            <li className="navbar-nav">
                                <button type="button" className="theme-danger btn-subtle rounded-0">Logga ut</button>
                            </li>
                        </ul> : 
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <a className="nav-link" href="/">Hem</a>
                            </li>
                            <li className="nav-item">
                                <a type="button" className="theme-primary btn-subtle rounded-0" href="/login">Logga in</a>
                            </li>
                        </ul>}
                    </div>
                </dialog>
            </div>
        </nav>
        {children}
        </>
    )
}
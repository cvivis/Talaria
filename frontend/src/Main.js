import { Link } from "react-router-dom";

function Main() {

    return (
        <div>
            <div>
                <p>에디터</p>
                <Link to={"/editor"}>에디터 이동</Link>
            </div>
            <div>
                <p>UI</p>
                <Link to={"/ui"}>UI 이동</Link>
            </div>
        </div>
    )
}

export default Main;
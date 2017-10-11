import React, {Component} from "react";

class Sector extends Component {

  render() {
    let name = this.props.name;
    for (let i = 0; i < this.props.depth; i++) {
      name = "\u00A0\u00A0" + name;
    }
    return(
      <option value={this.props.value}><span>{name}</span></option>
    );
  }
}

export default Sector;
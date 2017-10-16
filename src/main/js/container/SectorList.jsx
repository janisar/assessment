import React, {Component} from "react";
import Sector from "./Sector.jsx";
import TextInput from "./TextInput.jsx";

class SectorList extends Component {

  handleSectorsChange(_this) {
    return function (e) {

      let options = e.target.options;
      let value = [];
      for (let i = 0, l = options.length; i < l; i++) {
        if (options[i].selected) {
          value.push(options[i].value);
        }
      }

      _this.validation(value);
      if (_this.props.onChange) {
        _this.props.onChange(value);
      }
    }
  }

  constructor() {
    super();
    this.state = {
      sectors: [],
      value: null,
      valid: false,
      errorMessage: "At least one sector must be selected",
      errorVisible: false
    };
  }

  validation(value) {
    let valid = true;
    let errorVisible = false;

    if (value.length <= 0) {
      valid = false;
      errorVisible = true;
    }

    this.setState({
      value: value,
      valid: valid,
      errorVisible: errorVisible
    });
  }

  componentDidMount() {
    fetch(`http://localhost:8080/rootSectors`)
      .then(result => result.json())
      .then(items => this.setState({sectors: items}));
  }

  sectorIsSelected(sectorId) {
    return this.props.value && this.props.value.filter(elem => elem === sectorId).length === 1;
  }

  generateSectorRecursively(sector, depth) {
    let sectors = [<Sector value={sector.id} name={sector.name} depth={depth} selected={this.sectorIsSelected(sector.id)}/>];
    if (sector.children && sector.children.length > 0) {
      sector.children.forEach(sector => sectors.push(this.generateSectorRecursively(sector, depth + 1)));
    }
    return sectors;
  }

  render() {
    let sectors = [];
    this.state.sectors.forEach(sector => sectors.push(this.generateSectorRecursively(sector, 0)));

    return (
      <div className="form-elem">
        <label>Sectors:</label>
        <select multiple size="5" onChange={this.handleSectorsChange(this)}>
          {sectors}
        </select>
        <TextInput visible={!this.state.valid && this.state.errorVisible} message={this.state.errorMessage}/>
      </div>
    )
  }

}

export default SectorList;
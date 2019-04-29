<template>
  <button
    v-on:click="$emit('click')"
    v-bind:class="classList"
    v-on:mouseover="$props.shake ? hoverShake($event,8,60) : $emit('mouseover')"
    v-on:mouseleave="$props.shake ? cancelShake() : $emit('mouseleave')"
  >
  <slot></slot>
  </button>
</template>

<script>

export default {
    name: "beike-button",
    props: {
        type:{
            type: String,
            default: 'plain'
        },
        round:{
            type: Boolean,
            default: false
        },
        shake:{
            type: Boolean,
            default: false
        },
        hoverEffect:{
            type: Boolean,
            default: false
        },
        gpu:{
            type: Boolean,
            default: false
        }

    },

    data:function(){
        return {
            classList:'',
            shakeStatus: false,
            hoverShake: function(){},
            cancelShake: function(){}
        }
    },
    methods: {
        getElement: function(){
            return this.$el;
        },
        //渲染
        render: function () {
            let {type, round, shake, hoverEffect, gpu} = this.$props;
            let that = this;
            if(round)
                that.$data.classList +=  ' is-round';
            if((hoverEffect || shake) && gpu)
                that.$data.classList += ' gpu';
            if(hoverEffect && !shake)
                that.$data.classList += ' hover-effect';
            if(shake) {
                that.$data.hoverShake = this.hoverShakeCopy;
                that.$data.cancelShake = function () {
                    that.$data.shakeStatus = false
                };
            }
            if(type==='primary'){
                that.$data.classList += ' primary';
                return;
            }
            if(type==='warning'){
                that.$data.classList += ' warning';
            }
        },

        //抖动
        hoverShakeCopy: function(event,deg,time) {
            let element = event.target || event.srcElement;
            let id = null;
            let that = this;
            that.$data.shakeStatus = true;
            function shake(element,direction,deg,time) {
                if(!that.$data.shakeStatus){
                    element.style['transform'] = 'rotate(0deg)';
                    return ;
                }
                clearTimeout(id);
                id = setTimeout(()=>{
                    element.style['transform'] = 'rotate(' + (direction?'':'-') + deg + 'deg)';
                    shake(element,!direction,deg,time);
                },time);
            }
            setTimeout(shake(element,true,deg,time),time);
        },

        //动画帧抖动
        hoverShakeCopy_requestAnimationFrame: function(event,deg,time) {
            let element = event.target || event.srcElement;
            let that = this,
                direction = true;
            that.$data.shakeStatus = true;
            function shake() {
                if(!that.$data.shakeStatus){
                    element.style['transform'] = 'rotate(0deg)';
                    return ;
                }
                element.style['transform'] = 'rotate(' + (direction?'':'-') + deg + 'deg)';
                direction = !direction;
                window.requestAnimationFrame(shake);
            }
            window.requestAnimationFrame(shake);
        },

    },

    mounted(){
        this.render();
    }

}
</script>

<style scoped>
button{
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    letter-spacing: 1px;
    cursor: pointer;
    background: #fff;
    border: 1px solid #dcdfe6;
    color: #606266;
    -webkit-appearance: none;
    text-align: center;
    box-sizing: border-box;
    outline: none;
    margin: 0;
    transition: .1s;
    font-weight: 500;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    padding: 12px 20px;
    font-size: 14px;
    border-radius: 4px;
    transform: scale(1,1);
}
.warning{
    color: #fff;
    background-color: #f56c6c;
    border-color: #f56c6c;
}
.primary {
    color: #fff;
    background-color: #409eff;
    border-color: #409eff;
}
.is-round{
    border-radius: 20px;
    padding: 12px 23px;
}
.hover-effect:hover{
    transform: scale(1.1,1.1);
}
.gpu{
    will-change: transform;
}


</style>
